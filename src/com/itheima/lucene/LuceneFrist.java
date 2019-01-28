package com.itheima.lucene;


import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Path;

public class LuceneFrist {

    /*
    * 使用默认分析器
    * */
    @Test
    public void testLucene() throws Exception {
        //创建一个Director对象，指定索引库保存的位置(储存到硬盘上)
         Directory directory = FSDirectory.open(new File("F:\\lucene\\index").toPath());
         //基于directory对象，创建IndexWrite对象
        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig());
        //读取磁盘上的文件，对应每一个文件创建一个文档对象
        File dir = new File("F:\\java框架之后\\6.lecene\\1.lucene2018\\02.参考资料\\searchsource");
        File[] files = dir.listFiles();
        for (File file : files) {
            //取文件名
            String fileName = file.getName();
            //取文件路径
            String filePath = file.getPath();
            //文件的内容
            String fileContext = FileUtils.readFileToString(file, "utf-8");
            //文件的大小
            long fileSize = FileUtils.sizeOf(file);
            //创建Filed(域)
            //参数1: 域名称，参数2：域的内容，参数3：是否储存
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            Field fieldContext = new TextField("context", fileContext, Field.Store.NO);
            Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field fieldSize= new TextField("size",fileSize+"",Field.Store.YES);
            //创建文档对象
            Document document = new Document();
            //向文档对象中添加域
            document.add(fieldName);
            document.add(fieldContext);
            document.add(fieldPath);
            document.add(fieldSize);
            //把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //关闭indexWriter对象
        indexWriter.close();

    }
    @Test
    public void searchIndex() throws Exception {
        //创建Director对象，指定索引库的位置
        Directory directory = FSDirectory.open(new File("F:\\lucene\\index").toPath());
        //创建一个IndexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建一个IndexSearch对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //创建一个Query对象
        Query query = new TermQuery(new Term("name", "spring"));
        //执行查询，得到一个TopDocs 对象
        //参数1：查询对象 ， 参数2：查询返回的最大记录数
        TopDocs topDocs = indexSearcher.search(query, 10);
        //查询总记录数
        System.out.println("查询总记录数："+topDocs.totalHits);
        //取文档列表
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //打印文档中的内容
        for (ScoreDoc scoreDoc : scoreDocs) {
            //取文档id
            int docId = scoreDoc.doc;
            //根据id取文档对象
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));
            System.out.println(document.get("context"));
            System.out.println("-----------------------");

        }
        //释放资源
        indexReader.close();
    }

    @Test
    public void testTokenStream() throws Exception {
        //创建一个Analyzer对象，StandardAnalyzer对象(这里使用的是Lucene提供的默认的分析器)
        //Analyzer analyzer = new StandardAnalyzer();

        Analyzer analyzer = new IKAnalyzer();
        //使用分析器对象的tokenStream方法，创建TokenStream对象
        //参数1：文件名  参数2：文件内容
        TokenStream tokenStream = analyzer.tokenStream("","1.2 数据库like查询和全文检索的区别");
        //向TokenStream对象设置一个引用，相当于一个指针
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //调用TokenStream对象的reset方法，如果不调用抛异常
        tokenStream.reset();
        //遍历tokenStream对象（即遍历分词后的结果）
        while (tokenStream.incrementToken()){
            System.out.println(charTermAttribute.toString());
        }
        //关闭tokenStream对象
        tokenStream.close();
    }
    /*
    * 使用自定义发分析器
    * */
    @Test
    public void testIKAnalyzer() throws Exception {
        //创建一个Director对象，指定索引库保存的位置(储存到硬盘上)
        Directory directory = FSDirectory.open(new File("F:\\lucene\\index").toPath());
        //使用自定义分析器IKAnalyzer
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new IKAnalyzer());
        //基于directory对象，创建IndexWrite对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //读取磁盘上的文件，对应每一个文件创建一个文档对象
        File dir = new File("F:\\java框架之后\\6.lecene\\1.lucene2018\\02.参考资料\\searchsource");
        File[] files = dir.listFiles();
        for (File file : files) {
            //取文件名
            String fileName = file.getName();
            //取文件路径
            String filePath = file.getPath();
            //文件的内容
            String fileContext = FileUtils.readFileToString(file, "utf-8");
            //文件的大小
            long fileSize = FileUtils.sizeOf(file);
            //创建Filed(域)
            //参数1: 域名称，参数2：域的内容，参数3：是否储存
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            Field fieldContext = new TextField("context", fileContext, Field.Store.NO);
            //使用StringField对象不会进行分词，会创建索引,如：姓名，身份证号
            Field fieldPath = new StoredField("path", filePath);
            //让数值类型进行索引，但不会储存，如果要储存数据，还需要提供
            Field fieldSizeValue= new LongPoint("size",fileSize);
            StoredField fieldSizeStore = new StoredField("size",fileSize );

            //创建文档对象
            Document document = new Document();
            //向文档对象中添加域
            document.add(fieldName);
            document.add(fieldContext);
            document.add(fieldPath);
            document.add(fieldSizeValue);
            document.add(fieldSizeStore);
            //把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //关闭indexWriter对象
        indexWriter.close();

    }
}
