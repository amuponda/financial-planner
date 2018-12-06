## Helpful heroku commands for app

### App Information
```bash
heroku apps:info
```
### Deployment
```bash
git checkout release
git pull
git merge develop
git commit
git push
./gradlew deploHeroku
```
### Logs
```
heroku logs -n 200
heroku logs --tail (live logs)
```
### DB dashboard
https://dashboard.jawsdb.com/maria/dashboard
