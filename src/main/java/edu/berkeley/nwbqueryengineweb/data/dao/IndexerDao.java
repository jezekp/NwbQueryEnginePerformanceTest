package edu.berkeley.nwbqueryengineweb.data.dao;

import edu.berkeley.nwbqueryengineweb.data.pojo.NwbData;
import edu.berkeley.nwbqueryengineweb.data.utils.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/***********************************************************************************************************************
 *
 * This file is part of the nwbQueryEngineWebInterface project

 * ==========================================
 *
 * Copyright (C) 2019 by Petr Jezek
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
 * IndexerDao, 2019/03/24 17:06 petr-jezek
 *
 **********************************************************************************************************************/

public class IndexerDao implements GenericDao<NwbData, File> {



    private String python = Const.PYTHON_LOCATION;

    private String indexerScript = Const.INDEXER_SCRIPT;

    private String indexDb = Const.INDEX_DB;

    private String fileFolder = Const.FILES_FOLDER;

    @Override
    public List<NwbData> getData(String query, File file) throws Exception {


        String[] params = new String[]{python, indexerScript, indexDb};
        PythonData pythonData = new PythonData();
        return pythonData.getData(params, query);

    }

    @Override
    public File[] getFiles() {
        return new File[] {new File(indexDb)};
    }

    @Override
    public String getRootDir() {
        return fileFolder;
    }
}
