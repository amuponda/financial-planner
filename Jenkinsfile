#!groovy

node {
	stage('checkout') {
		checkout scm
	}

	stage('check tools') {
		sh 'pwd'
		sh './gradlew clean'
	}

	stage('test') {
		sh './gradlew :server:test'
	}
}