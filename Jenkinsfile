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
	}

	stage('clean')
	{
		sh "chmod +x gradlew"
		sh "./gradlew clean --no-daemon"
	}

	stage('check integration')
	{
		try
		{
			sh 'docker container inspect docker_catinyuaa-elasticsearch_1'
			sh 'docker container inspect docker_catinyuaa-mariadb_1'
			sh 'docker container inspect docker_catinyuaa-redis_1'
			sh "docker container inspect docker_jhipster-registry_1"
			sh "docker container inspect docker_zookeeper_1"
			sh "docker container inspect docker_kafka_1"
		}
		catch (ignored)
		{
			echo 'the necessary services are not running . try start it'
			sh "docker-compose -f src/main/docker/app-prod.yml up -d"
			echo 'Sleep for 120 seconds to wait for the mariadb to be ready'
			sleep(120)
		}
	}

	stage('nohttp')
	{
		sh "./gradlew checkstyleNohttp --no-daemon"
	}

	stage('backend tests')
	{
		try
		{
//			sh "./gradlew build --no-daemon"
			sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
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

	stage('build docker catiny-uaa')
	{
		sh "./gradlew bootJar -Pprod jibDockerBuild --no-daemon"
	}

	stage('start docker catiny-uaa')
	{
		sh "docker-compose -f src/main/docker/catiny-uaa.yml up -d"
		echo "Successful deployment"
	}

	stage( 'Log display after 200 seconds from running')
	{
		sleep(60)
		sh "docker logs docker_catinyuaa-app_1 --tail 1000"
	}

}
