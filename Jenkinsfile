#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java , node , docker , docker-compose') {
        sh "java -version"
        sh "node -v"
        sh "npm -v"
        sh "docker -v"
        sh "docker-compose -v"
    }

    stage('clean') {
        sh "chmod +x gradlew"
        sh "./gradlew clean --no-daemon"
    }
    stage('nohttp') {
        sh "./gradlew checkstyleNohttp --no-daemon"
    }

    // todo test
//    stage('backend tests') {
//        try {
//            sh "./gradlew test integrationTest -PnodeInstall --no-daemon"
//        } catch(err) {
//            throw err
//        } finally {
//            junit '**/build/**/TEST-*.xml'
//        }
//    }

    stage('packaging') {
        sh "./gradlew bootJar -x test -Pprod -PnodeInstall --no-daemon"
        archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
    }

//    todo sonar
//    stage('quality analysis') {
//        withSonarQubeEnv('catiny-uaa-sonar') {
//            sh "./gradlew sonarqube --no-daemon"
//        }
//    }

    stage('check jhipster-registry'){
        try
        {
            sh "docker container inspect docker_jhipster-registry_1"
        }
        catch (err)
        {
            echo "docker_jhipster-registry_1 not running"
            sh "docker-compose -f src/main/docker/jhipster-registry-docker.yml up -d"
        }
    }

    stage('build docker catiny-uaa') {
        sh "./gradlew bootJar -Pprod jibDockerBuild"
    }

    stage('start docker catiny-uaa') {
        sh "docker-compose -f src/main/docker/mariadb.yml up -d"
    }

}
