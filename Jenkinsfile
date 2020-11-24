#!/usr/bin/env groovy

node {
	stage('checkout')
	{
		checkout scm
	}

	stage('check java , node , docker , docker-compose')
	{
		sh "pwd"
		sh "java -version"
		sh "node -v"
		sh "npm -v"
		sh "docker -v"
		sh "docker-compose -v"
		sh "docker service ls"
	}

	stage('clean')
	{
		sh "chmod +x gradlew"
		sh "./gradlew clean --no-daemon"
	}
	stage('nohttp')
	{
		sh "./gradlew checkstyleNohttp --no-daemon"
	}

	stage('check integration')
	{
		try
		{
			sh 'docker service inspect catiny-services_catinyuaa-elasticsearch'
			sh 'docker service inspect catiny-services_catinyuaa-mariadb'
			sh 'docker service inspect catiny-services_catinyuaa-redis'
			sh "docker service inspect catiny-services_jhipster-registry"
			sh "docker service inspect catiny-services_zookeeper"
			sh "docker service inspect catiny-services_kafka"
		}
		catch (ignored)
		{
			echo 'the necessary services are not running . try start it'
			sh "docker stack deploy -c /root/CatinyServer/swarm/catiny-services.yml catiny-services"
			echo 'Sleep for 150 seconds to wait for the mariadb to be ready'
			sleep(150)
		}
		try
		{
			sh 'docker service inspect catinydev-services_catinydevuaa-elasticsearch'
			sh 'docker service inspect catinydev-services_catinydevuaa-mariadb'
			sh 'docker service inspect catinydev-services_catinydevuaa-redis'
			sh "docker service inspect catinydev-services_jhipster-registry-dev"
			sh "docker service inspect catinydev-services_zookeeper-dev"
			sh "docker service inspect catinydev-services_kafka-dev"
		}
		catch (ignored)
		{
			echo 'the necessary services are not running . try start it'
			sh "docker stack deploy -c /root/CatinyServer/swarm/catiny-services.yml catiny-services"
			echo 'Sleep for 150 seconds to wait for the mariadb to be ready'
			sleep(150)
		}
	}

	stage('backend tests')
	{
		try
		{
			sh "./gradlew build"
		}
		catch (err)
		{
			throw err
		}
		finally
		{
			junit '**/build/**/TEST-*.xml'
		}
	}

	stage('check catiny app is running')
	{
		try
		{
			sh "docker stack ls | grep catiny-app-dev"
		}
		catch (ignored)
		{
			echo "catiny app dev or prod isn't running"
			sh "docker stack deploy -c /root/CatinyServer/swarm/catiny-app-dev.yml catiny-app-dev"
			sleep(60)
		}
		try
		{
			sh "docker stack ls | grep catiny-app-prod"
		}
		catch (ignored)
		{
			echo "catiny app dev or prod isn't running . try start"
			sh "docker stack deploy -c /root/CatinyServer/swarm/catiny-app-prod.yml catiny-app-prod"
			sleep(60)
		}
	}

	stage('build docker catiny-uaa')
	{
		try
		{
			sh "./gradlew bootJar -Pprod jibDockerBuild --no-daemon"
			sh "docker tag catinyuaa:latest yuvytung/catinyuaa:latest"
			sh "docker push yuvytung/catinyuaa:latest"
		}
		catch (exception)
		{
			throw exception
		}
	}

	stage('start docker catiny-uaa')
	{
		try
		{
			sh "docker service update --force catiny-app-dev_catinyuaa"
		}
		catch (exception)
		{
			throw exception
		}
		sh "docker service update --force catiny-app-prod_catinyuaa"
		echo "Successful deployment"
	}

	stage( 'Log display after 200 seconds from running')
	{
		sleep(60)
		sh "docker service logs --tail 500 catiny-app-dev_catinyuaa"
		sh "docker service logs --tail 500 catiny-app-prod_catinyuaa"
		echo "Done."
	}

}


//#!/usr/bin/env groovy
//
//node {
//	stage('checkout')
//	{
//		checkout scm
//	}
//
//	stage('check java , node , docker , docker-compose')
//	{
//		sh "pwd"
//		sh "java -version"
//		sh "node -v"
//		sh "npm -v"
//		sh "docker -v"
//		sh "docker-compose -v"
//	}
//
//	stage('clean')
//	{
//		sh "chmod +x gradlew"
//		sh "./gradlew clean --no-daemon"
//	}
//
//	stage('check integration')
//	{
//		try
//		{
//			sh 'docker container inspect docker_catinyuaa-elasticsearch_1'
//			sh 'docker container inspect docker_catinyuaa-mariadb_1'
//			sh 'docker container inspect docker_catinyuaa-redis_1'
//			sh "docker container inspect docker_jhipster-registry_1"
//			sh "docker container inspect docker_zookeeper_1"
//			sh "docker container inspect docker_kafka_1"
//		}
//		catch (ignored)
//		{
//			echo 'the necessary services are not running . try start it'
//			sh "docker-compose -f src/main/docker/app-prod.yml up -d"
//			echo 'Sleep for 120 seconds to wait for the mariadb to be ready'
//			sleep(120)
//		}
//	}
//
//	stage('nohttp')
//	{
//		sh "./gradlew checkstyleNohttp --no-daemon"
//	}
//
//	stage('backend tests')
//	{
//		try
//		{
////			sh "./gradlew build --no-daemon"
//			sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
//		}
//		catch (err)
//		{
//			throw err
//		}
//		finally
//		{
//			junit '**/build/**/TEST-*.xml'
//		}
//	}
//
//	stage('build docker catiny-uaa')
//	{
//		sh "./gradlew bootJar -Pprod jibDockerBuild --no-daemon"
//	}
//
//	stage('start docker catiny-uaa')
//	{
//		sh "docker-compose -f src/main/docker/catiny-uaa.yml up -d"
//		echo "Successful deployment"
//	}
//
//	stage( 'Log display after 200 seconds from running')
//	{
//		sleep(60)
//		sh "docker logs docker_catinyuaa-app_1 --tail 1000"
//	}
//
//}
