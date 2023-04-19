microcontainer=$(buildah from docker.io/library/eclipse-temurin:17-jre-alpine)
micromount=$(buildah mount $microcontainer)

buildah copy $microcontainer './target/server-0.0.1-SNAPSHOT.jar' '/home/cacao/app.jar'

buildah config --cmd "java -jar /home/cacao/app.jar" $microcontainer

rm -rf $micromount/var/cache $micromount/var/log/*

buildah umount $microcontainer
buildah rmi cacao
buildah commit $microcontainer cacao
buildah delete $microcontainer


