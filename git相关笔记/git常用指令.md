# git常用指令

1. git config --global user.name "用户名" 

   ```注释
   配置用户名
   ```

   

2. git config --global user.email "你的github的邮箱"

   ```注释
   配置邮箱
   ```

   

3. git clone + 仓库url

   ```注释
   把指定url的仓库内容克隆到本地
   ```

   

4. git add + 文件名

   ```注释
   把指定文件放在缓存区
   git add . //表示把当前目录下的所有文件都放进缓存区
   ```

   

5. git commit

   ```注释
   把我们放进缓存区里面的内容放进远程仓库(这个仓库可能是github,可能是gitee,也可能是我们自己搭建的仓库)
   git commit -m "注释内容"  //加上-m参数可以添加我们的注释
   ```

   

6. git log

   ```注释
   查看我们的git日志,比如提交项目的人,提交时间,提交备注等
   git log --stat  //加上--stat就可以知道每次具体修改了哪些文件
   ```

   

7. git diff + [commit id]

   ```注释
   查看我们指定的版本
   commit id:我们在使用git log 或者 git log --stat可以获得
   
   ```

   ![image-20240328134636114](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240328134636114.png)

   

8. git reset --hard + [commit id]

   ```注释
   回溯到我们指定的版本
   ***注意，回溯到之前的版本，想要回到现在的版本要用
   git reflog --pretty=oneline
   ```

   ![image-20240328134727426](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240328134727426.png)

9. 分支的概念 : 现在有这样一个情况,我们要开发一个app，但是一个是中文版,一个是英文版 ，虽然版本不同，但实际上时同一个软件，这样这两个软件就产生了分支

   ```注释
   git branch //可以查看当前版本有哪些分支
   git checkout -b develop //开辟一个名为的分支
   git checkout + 指定分支名  //切换到我们指定的一个分支
   git merge + 要合并的分支名  //把我们指定的分支和当前分支合并
   ```

   ![image-20240328134927614](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240328134927614.png)

10. 查看文件的状态：git status

   1. 未被跟踪状态 ![image-20240401150429767](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401150429767.png)

   2. 已被跟踪状态,并处于暂存的状态![image-20240401150524689](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401150524689.png)

   3. 表示所有的文件都处于未修改，没有任何文件需要被提交![image-20240401151004401](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401151004401.png)

   4. 表示已提交的文件被修改![image-20240401151138876](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401151138876.png)

      ```把我们已修改的文件重新提交到仓库
      暂存我们已经修改了的文件
      git add + 我们修改了的文件
      ```

      

   5. 修改后的文件表示已经放入暂存区（暂存和提交不是一回事喔）![image-20240401151735634](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401151735634.png)

      ```提交暂存区里面的内容
      git commit -m "注释内容"
      ```

      

   6. 把暂存区里的东西提交![image-20240401152038860](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401152038860.png)

11. git中的四种状态：只有管理和已被管理两种,已被管理只有未追踪,已被管理则有未修改、已修改、已暂存三种![image-20240401145729251](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401145729251.png)

12. 撤销对文件的修改: git checkout -- 我们要撤销的文件的名字  //这条指令是不可逆的，谨慎使用

13. 取出放入暂存区里面的文件：git reset HEAD + 我们要取出的文件的名字

14. 跳过暂存区的步骤，直接把修改了的文件提交到远程仓库：git commit -a -m "注释"

15. 移除文件的指令

    ```指令
    //从仓库和工作区同时移除index.js文件
    git rm -f index.js
    //从仓库中移除文件Index.css,但保留工作区中的index.css文件
    git rm --cached index.css
    
    
    ```

    

16. 忽略文件

    ```解释
    .gitignore文件里面的内容,可以被git指令忽略
    
    ```

    

17. glob模式来匹配文件![image-20240401154607582](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401154607582.png)

18. .gitignore文件的具体使用![image-20240401155418362](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20240401155418362.png)




 # git与github的使用



## 		使用https的方式链接github

如果本地有现成的git仓库，只需两部走

```使用https的方式
//将本地仓库和远程仓库进行关联，并把远程仓库命名为origin
git remote add origin + url

//将本地仓库中的内容推送到远程的origin仓库中
git push -u origin master(第一次使用的时候才需要)

//如果不是第一次上次代码
git push
```



## 使用ssh的方式连接github

```
//在本地生成密钥
ssh-keygen -t rsa -b 4096 -C "2991906462@qq.com"

//在C:\Users\用户名文件夹\.ssh目录中生成id_rsa和id_rsa.pub
id_rsa.pub是公钥，我们将其放到github上

//检测ssh Key是否配置成功
ssh -T git@github.con

//将本地仓库的文件提交到github两步走
git remote add origin + url
git push -u origin master(第一次才需要-u,后面提交只需要git push)



```



# 分支



### 第一次把本地分支上传到远程分支

```
//第一次把本地分支上次到远程分支
git push -u 远程仓库的别名  本地分支名称:远程分支名称 (后面使用直接git push就可以了)

//如果希望远程分支和本地分支保存一致
git push -u 本地分支名称 

```

### 查看所有远程分支

```
//查看远程所有分支
git remote show origin
```

### 远程仓库的分支下载到本地仓库

```
//从远程仓库中，把对应的远程分支下载到本地仓库
git checkout 远程分支的名称 (如果本地有就切换到对应分支，如果本地没有就从远程仓库中下载指定分支)

//从远程仓库中把分支下载到本地,并且多分支重命名
git checkout -b 本地分支名称 远程仓库名称/远程分支名称

```



### 远程分支的最新代码下载到本地

```
//从远程分支中下载最新代码带本地
git pull
```



### 删除远程分支

```
//删除远程分支
git push 远程仓库名称 --delete  远程分支名称

//删除本地分支
git branch -d 本地分支名称
```









