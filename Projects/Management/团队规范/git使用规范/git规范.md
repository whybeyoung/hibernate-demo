
## 代码开发

使用开发分支提交代码,不要使用master分支

首先检查本地分支：`git branch`

如果不存在本地开发分支，新建开发分支：`git branch -b yourfeature`

如果存在本地开发分支，切换到开发分支：`git checkout yourfeature`



## 同步代码

分支开发过程，同步主干代码

`git fetch origin`

`git rebase origin/master`

同步主干代码前，先暂存分支开发状态

`git stash`

`git stash`使用方法参考[这里](https://git-scm.com/book/zh/v1/Git-%E5%B7%A5%E5%85%B7-%E5%82%A8%E8%97%8F%EF%BC%88Stashing%EF%BC%89)


## 提交代码

代码提交

`git add .`

`git commit -m "注释"`


## 代码推送到远程仓库

如果推送代码前有多次commit，建议合并commit

`git commit --fixup`

`git rebase -i --autosquash `

推送到远程仓库

`git push --force origin myfeature`


## 合并请求

登录git.iflytek.com,提交merge request.






