# SSH Maven Archetype

把当前 SSH 脚手架打包成 `tech.zqxx:ssh-archetype:0.0.1`，生成的新项目可直接在 Eclipse 的
`File -> New -> Maven Project -> Select an Archetype` 中选择。

## 构建并安装到本地仓库

在项目根目录执行：

```bat
mvn -B -f ssh-archetype\pom.xml clean install
```

安装后会生成：

```text
D:\dev_tools\maven\repository\tech\zqxx\ssh-archetype\0.0.1\ssh-archetype-0.0.1.jar
D:\dev_tools\maven\repository\archetype-catalog.xml
```

`maven-archetype` 打包会在 `install` 阶段自动执行 `archetype:update-local-catalog`。
如果本机 Maven 本地仓库不是 `D:\dev_tools\maven\repository`，以实际 `settings.xml`
中的 `localRepository` 为准。

## 在 Eclipse 中使用

1. `File -> New -> Maven Project`。
2. 不要勾选 `Create a simple project (skip archetype selection)`，点击 `Next`。
3. 在 `Select an Archetype` 页面，把 `Catalog` 切换为 `Local`（或 `All Catalogs`）。
4. 找到并选择：
   - Group Id：`tech.zqxx`
   - Artifact Id：`ssh-archetype`
   - Version：`0.0.1`
5. 点击 `Next`，填写新项目的 Group Id、Artifact Id、Version 和 **Package**。
   - `Package` 要填写 **Java 根包**，例如 `com.example.demo`。
   - 不要填写 `com.example.demo.common`；模板会自己生成 `.common`、`.common.entity` 等子包。
6. 点击 `Finish`，等待 m2e 下载依赖并构建。

如果 `Local` 目录没有出现：

- 在 `Window -> Preferences -> Maven -> Archetypes` 中点击 `Reload`，或重启 Eclipse。
- 点击 `Select an Archetype` 页面上的 `Add Archetype...`，直接填写上面的 Group Id、
  Artifact Id、Version，Repository 留空或选择本地仓库后 `OK`。
- 或者 `Add Remote Catalog...`，Catalog File 填：
  ```text
  file:///D:/dev_tools/sts/workspace/ssh/ssh-archetype/archetype-catalog.xml
  ```

## 命令行生成

```bat
mvn archetype:generate ^
  -DarchetypeCatalog=local ^
  -DarchetypeGroupId=tech.zqxx ^
  -DarchetypeArtifactId=ssh-archetype ^
  -DarchetypeVersion=0.0.1 ^
  -DgroupId=com.example ^
  -DartifactId=demo-app ^
  -Dversion=1.0.0-SNAPSHOT ^
  -Dpackage=com.example.demo ^
  -DinteractiveMode=false
```

生成后的目录：

```text
demo-app/
  src/main/java/com/example/demo/common/APIController.java
  src/main/java/com/example/demo/common/entity/DemoItem.java
  src/main/java/com/example/demo/common/service/DemoItemService.java
  src/main/java/com/example/demo/common/config/RedissonClientFactoryBean.java
```

## 模板说明

```text
ssh-archetype/
  pom.xml                                  # maven-archetype 工程
  archetype-catalog.xml                    # 可被 Eclipse 作为文件 Catalog 加载
  src/main/resources/
    META-INF/maven/archetype-metadata.xml   # 文件集和 package 默认值
    archetype-resources/                    # 真正的项目模板
      pom.xml
      README.md
      src/main/java/common/...              # packaged=true，生成时加上 package 路径
      src/main/resources/...
      src/main/webapp/...
```

当前 Java 模板使用 `${package}.common`，因此 `package` 参数必须是根包。`spring-core.xml`、
`spring-mvc.xml` 中的组件扫描包，以及 `redis.properties` 中的 Session 命名空间，都会在生成时
替换为实际项目参数。

修改主项目代码后，需要同步更新 `archetype-resources`，再重新执行 `mvn -f ssh-archetype\pom.xml
clean install`。也可以用下面的命令从主项目重新生成模板，但生成结果会包含 `.settings`、日志等
无关文件，建议只复制 `src/main`、`pom.xml` 和需要的 README：

```bat
mvn -B archetype:create-from-project -DpackageName=tech.zqxx
```
