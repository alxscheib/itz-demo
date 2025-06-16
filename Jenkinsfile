pipeline {
    agent any

    tools {
        maven 'Maven 3.9.10'
        jdk 'JDK 17'
    }

    environment {
        SONAR_TOKEN = credentials('sonar-token')
    }

    triggers {
        pollSCM('H/20 * * * *') // läuft automatisch alle 20 Minute
    }

    options {
        skipDefaultCheckout(true)
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '**']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/alxscheib/itz-demo.git'
                    ]]
                ])
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Static Code Analysis') {
            steps {
                sh '''
                  mvn com.github.spotbugs:spotbugs-maven-plugin:spotbugs \
                      org.apache.maven.plugins:maven-checkstyle-plugin:checkstyle \
                      org.apache.maven.plugins:maven-pmd-plugin:pmd
                '''
            }
            post {
                always {
                    recordIssues(tools: [
                        spotBugs(pattern: '**/spotbugsXml.xml'),
                        checkStyle(pattern: '**/checkstyle-result.xml'),
                        pmdParser(pattern: '**/pmd.xml')
                    ])
                }
            }
        }

        stage('Code Quality - SonarCloud') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
    }

    post {
        success {
            echo '✅ Build, Test und Analyse erfolgreich!'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        failure {
            echo '❌ Fehler beim Build oder bei der Analyse!'
        }
    }
}
