pipeline {
    agent any
    
    environment {
        SONARQUBE_TOKEN = 'sqa_e4c3910c0ac4bb36fc821780e8553f35b2d31366'
        SONARQUBE_URL = 'http://localhost:9000'
        DOCKER_IMAGE = 'emnabouaziz/tp-foyer:2.0.0'
        BACKEND_URL = 'http://localhost:8089'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git credentialsId: 'git-credentials',
                    branch: 'foyer-emnabouaziz',
                    url: 'https://github.com/emnabouaziz/Gestion-Foyer.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Unit Tests and JaCoCo') {
            steps {
                sh 'mvn clean test jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=tp-foyer \
                        -Dsonar.host.url=${SONARQUBE_URL} \
                        -Dsonar.login=${SONARQUBE_TOKEN} \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    """
                }
            }
        }

        stage('Package with Maven') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-credentials', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    sh 'mvn deploy -Dnexus.username=${NEXUS_USERNAME} -Dnexus.password=${NEXUS_PASSWORD}'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                sh """
                    docker login -u emnabouaziz -p 223AFT2365
                    docker push ${DOCKER_IMAGE} 
                """
            }
        }

        stage('Docker Compose') {
            steps {
                sh 'docker-compose up -d'
            }
        }
    }

    post {
        success {
            emailext(
                subject: "Application Backend is Running Successfully - Job #${env.BUILD_NUMBER}",
                body: """
                    <p>Hi Emna,</p>
                    <p>The backend application has been successfully deployed and is now running.</p>
                    <p><strong>Application URL:</strong> <a href="${BACKEND_URL}">${BACKEND_URL}</a></p>
                    <p>Check Jenkins for more details: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p>Regards,<br>Jenkins</p>
                """,
                mimeType: 'text/html',
                to: "chaimaskouri5654@gmail.com"
            )
        }
        failure {
            emailext(
                subject: "Application Deployment Failed - Job #${env.BUILD_NUMBER}",
                body: """
                    <p>Hi Emna,</p>
                    <p>Unfortunately, the deployment of the backend application has failed.</p>
                    <p>Please review the logs in Jenkins for more details: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p>Regards,<br>Jenkins</p>
                """,
                mimeType: 'text/html',
                to: "chaimaskouri5654@gmail.com"
            )
        }
    }
}
