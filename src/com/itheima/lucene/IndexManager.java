package com.itheima.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class IndexManager {

    public IndexWriter indexWriter;
    @Before
    public void init() throws Exception{
        //创建一个IndexWriter对象，需要使用Analyzer作为分析器
        indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\lucene\\index").toPath()),
                new IndexWriterConfig(new IKAnalyzer()));
    }

    /*
    * 添加文档
    * */
    @Test
    public void addDocument() throws Exception {
        //创建一个Document对象
        Document document = new Document();
        //向document对象中添加域
        document.add(new TextField("name","新添加的文件", Field.Store.YES));
        document.add(new TextField("context","新添加文件的内容",Field.Store.NO));
        document.add(new StoredField("path","c:\\temp\\hello"));
        //将文档对象写入索引库
        indexWriter.addDocument(document);
        //关闭indexWriter对象
        indexWriter.close();
    }
    /*
    * 删除文档。根据查询、关键词删除文档
    * */
    @Test
    public void deleteDocumentByQuery() throws Exception {
        long del = indexWriter.deleteDocuments(new Term("name", "apache"));
        System.out.println(del);
        indexWriter.close();
    }

    /*
    * 更新文档。先删除，再更新
    * */
    @Test
    public void updateDocument() throws Exception {
        //创建一个新文档对象
        Document document = new Document();
        //向文档对象中添加域
        document.add(new TextField("name","更新之后的文档", Field.Store.YES));
        document.add(new TextField("name1","更新之后的文档2", Field.Store.YES));
        document.add(new TextField("name2","更新之后的文档3", Field.Store.YES));
        //更新操作
        indexWriter.updateDocument(new Term("name","spring"),document);
        //关闭索引库
        indexWriter.close();

    }
}
