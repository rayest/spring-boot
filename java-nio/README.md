## JAVA-NIO

* 读文件

> 1. 分配缓冲区：
>
>    ```java
>    ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
>    ```
>
>    2. 通过文件路径 filepath 获取文件输入流 FileInputStream 最后获取到文件通道 FileChannel。
>
> ```java
>// 使用了装饰者模式
> FileInputStream fileInputStream = new FileInputStream(new File(pathname));
>    FileChannel channel = fileInputStream.getChannel();
>    ```
>    
>    3. 将通道内数据读取到缓冲区内
>
> ```java
>channel.read(byteBuffer); // 根据缓冲区大小读取指定的字节数
> ```
> 

