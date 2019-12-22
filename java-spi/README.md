## java spi

* 什么是 SPI
> SPI 全称为 Service Provider Interface，是一种服务发现机制。
SPI 的本质是将接口实现类的全限定名配置在文件中，并由服务加载器 serviceLoader 读取配置文件，加载实现类。
这样可以在运行时，动态为接口替换实现类。
正因此特性，我们可以很容易的通过 SPI 机制为我们的程序提供拓展功能。
但是，Java SPI 会加载并初始化所有的配置的实现类

* dubbo SPI
> Dubbo 就是通过 SPI 机制加载所有的组件。
Dubbo 并未使用 Java 原生的 SPI 机制，而是对其进行了增强，使其能够更好的满足需求.
基于 SPI，我们可以很容易的对 Dubbo 进行拓展。
  
* Java SPI demo
> 1. 定义接口和接口的实现类
> 2. 在 resources 目录下新建 META-INF/services 目录
> 3. 创建名为接口全路径的文件 com.rayest.spi.CalculatorService
> 4. 文件内容为接口实现类的全路径名 
>> com.rayest.spi.impl.SubtractionServiceImpl
>> com.rayest.spi.impl.AddServiceImpl
> 5. 在测试方法中，通过 ServiceLoader.load() 方法加载接口，会初始化两个实现类
