#### spring-security-sdk-demo
##### 1.Test构建MockMvc
- 1.1 原始方法；该方法属于常规方法

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
```
- 1.2 官方推荐方法；虽然推荐这么做，但是当项目集成spring security
的时候会报401未授权异常，即使你配置了Config类，还是会报错。猜测是
因为它的别的一些也在启动的原因。（待推敲）
```java
@RunWith(SpringRunner.class)
@WebMvcTest(UserVehicleController.class)
public class MyControllerTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserVehicleService userVehicleService;

	@Test
	public void testExample() throws Exception {
		given(this.userVehicleService.getVehicleDetails("sboot"))
				.willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andExpect(content().string("Honda Civic"));
	}

}
```