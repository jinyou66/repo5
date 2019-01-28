package com.itheima.lucene;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class SearchIndex {

    private DirectoryReader indexReader;
    private IndexSearcher indexSearcher;

    @Before
    public void init() throws Exception {
        indexReader = DirectoryReader.open(FSDirectory.open(new File("F:\\lucene\\index").toPath()));
        indexSearcher = new IndexSearcher(indexReader);
    }

    @Test
    public void testRangeQuery() throws Exception {
        //创建一个Query对象
        Query query = LongPoint.newRangeQuery("size", 0L, 100L);

        printResult(query);
    }
    /*
    * 抽取查询结果，封装成一个方法
    * */
    private void printResult(Query query) throws Exception {
        //执行查询， 查询返回最大记录数
        TopDocs topDocs = indexSearcher.search(query, 10);
        //查询总记录数
        System.out.println("查询总记录数：" + topDocs.totalHits);
        //读取文档列表
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //遍历scoreDocs 对象
        for (ScoreDoc scoreDoc : scoreDocs) {
            //获取文档的id
            int docId = scoreDoc.doc;
            //根据id获取文档对象
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println(document.get("context"));
            System.out.println("-----------------------");
        }
    }
    /*
    * 根据一段话进行查询的实现
    * */
    @Test
    public void testQueryParser() throws Exception {
        //创建一个QueryParser对象，俩个参数
        //参数1：默认搜索域  参数2：中文分析器
        QueryParser queryParser = new QueryParser("name", new IKAnalyzer());
        //使用queryParser对象，创建Query对象
        Query query = queryParser.parse("Lucene是一个基于Java开发的全文检索的工具包");
        //执行查询，返回查询结果
        printResult(query);


    }
}
