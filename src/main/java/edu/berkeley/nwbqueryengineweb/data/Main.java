package edu.berkeley.nwbqueryengineweb.data;

import edu.berkeley.nwbqueryengineweb.data.dao.GenericDao;
import edu.berkeley.nwbqueryengineweb.data.dao.IndexerDao;
import edu.berkeley.nwbqueryengineweb.data.dao.NwbDao;
import edu.berkeley.nwbqueryengineweb.data.dao.SearchPythonDao;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/***********************************************************************************************************************
 *
 * This file is part of the NwbQueryEnginePerformanceTest project

 * ==========================================
 *
 * Copyright (C) 2019 by University of West Bohemia (http://www.zcu.cz/en/)
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************
 *
 * Main, 2019/07/31 11:37 petr-jezek
 *
 **********************************************************************************************************************/
public class Main {

    public static void main(String[] args) throws Exception {

        System.loadLibrary("HDFql");

        String[] queries = {"epochs:(start_time>200 & stop_time<400 | stop_time>1600)",
        "*/data: (unit == \"unknown\")",
        "/general/subject: (subject_id == \"anm00210863\") & */epochs/*: (start_time > 500 & start_time < 550 & tags LIKE \"%LickEarly%\")",
        "/units: (id > -1 & location == \"CA3\" & quality > 0.8)",
        "/general:(virus LIKE \"%infectionLocation: M2%\")"};

        GenericDao indexerDao = new IndexerDao();
        GenericDao nwbDao = new NwbDao();
        GenericDao searchPythonDao = new SearchPythonDao();
        System.out.println("Tools comparision results");
        System.out.println(";NWB Indexer;NWB Query Engine;Search NWB");
        for(String query : queries) {

            File[] indexerDaoFiles = indexerDao.getFiles();
            double indexerResult = make(query, indexerDaoFiles, indexerDao);


            File[] nwbDaoFiles = nwbDao.getFiles();
            String tmpQuery = query.replaceAll("\\*/", "");
            double nwbResult = make(tmpQuery, nwbDaoFiles, nwbDao);

            File[] searchPythonDaoFiles = searchPythonDao.getFiles();
            double searchNwb = make(query, searchPythonDaoFiles, searchPythonDao);


            System.out.println(query + ";" + indexerResult + ";" + nwbResult + ";" + searchNwb);


        }

    }

    public static double make(String query, File[] files, GenericDao dao) throws Exception {
        int count = 10;
        long[] array = new long[count];
        for(int i = 0; i < array.length; i++) {
            long timeStart = System.currentTimeMillis();
            for (File file : files) {
                dao.getData(query, file);
            }
            long timeConsumed = System.currentTimeMillis() - timeStart;
            array[i] = timeConsumed;
        }

        return findAverageUsingStream(array);


    }

    public static double findAverageUsingStream(long[] array) {
        return Arrays.stream(array).average().orElse(Double.NaN);
    }
}
