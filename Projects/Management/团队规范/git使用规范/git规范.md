## 克隆代码库

`git clone git@192.168.65.10:CBG_AI_Cloud2.0/MiguIaas.git`



## 代码开发

使用开发分支提交代码,不要使用master分支

首先检查本地分支：`git branch`

如果不存在本地开发分支，新建开发分支：`git branch -b yourfeature`

如果存在本地开发分支，切换到开发分支：`git checkout yourfeature`

    分支命名规范：
                域账号/分支名
                xwliu/myfeature



## 提交代码

代码提交

`git add .`

`git commit -m "注释"`


## 同步代码

分支开发过程，同步主干代码

`git fetch origin`

`git rebase origin/master`



## 代码推送到远程仓库


推送到远程仓库

`git push -u origin yourfeature`


## 合并请求

登录git.iflytek.com,提交merge request.






