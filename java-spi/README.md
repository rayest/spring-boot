## java spi

* Java SPI demo
> 1. 定义接口和接口的实现类
> 2. 在 resources 目录下新建 META-INF/services 目录
> 3. 创建名为接口全路径的文件 com.rayest.spi.PrintService
> 4. 文件内容为接口实现类的全路径名 com.rayest.spi.PrintServiceImpl
> 5. 在测试方法中，通过 ServiceLoader.load() 方法加载接口
