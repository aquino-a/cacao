microcontainer=$(buildah from registry.fedoraproject.org/eclipse-temurin:17-jdk-alpine)
micromount=$(buildah mount $microcontainer)

buildah copy $microcontainer './target/blah.jar' '/home/cacao/app.jar'

npm install --prefix $micromount/home/comment-delete

buildah config --cmd "java -jar /home/cacao/app.jar" $microcontainer

rm -rf $micromount/var/cache $micromount/var/log/*

buildah umount $microcontainer
buildah rmi cacao
buildah commit $microcontainer cacao
buildah delete $microcontainer


