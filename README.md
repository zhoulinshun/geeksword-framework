# geeksword-framework
geeksword核心仓库


## maven

###依赖线
geeksword-build <- geeksword-dependencies <- geeksword-parent <- geeksword-other

###依赖规范
为了避免依赖出现冲突，所有子项目中引入的依赖都必须在geeksword-dependencies中预声明