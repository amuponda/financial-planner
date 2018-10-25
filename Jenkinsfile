#!groovy

pipeline {
	agent any
	stages {
		stage('checkout') {
			steps {
				checkout scm
			}
		}

		stage('check tools') {
			steps {
				sh 'pwd'
				sh './gradlew clean'
			}
		}

		stage('test') {
			steps {
				sh './gradlew :server:test'
			}
		}

		stage('deploy') {
			when {
				branch 'release'
			}
			steps {
				sh './gradlew deployHeroku'
			}
		}
	}
}