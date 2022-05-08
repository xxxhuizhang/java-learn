
#docker

docker images;
docker ps
docker ps -a ;
docker stats;

docker update mysql --restart=always
docker exec -it postgres /bin/bash


#compose
docker-compose -f docker-env-mac.yml up -d postgres

 docker network ls

# 删除报错的两个 product-server_default 即可：
docker network rm d95512e66713 
docker network rm 8b094834a5c1

docker-compose -f /Users/apple/Desktop/java/java-learn/learn/docker/docker-env-mac.yml up -d nginx



