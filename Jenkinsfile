pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'vantran102/silkroadv2:vtest'
        DOCKERHUB_CREDENTIALS_ID = 'dockerhub-credentials'   // ID trong Jenkins Credential cho Docker Hub
        PRIVATE_KEY_CREDENTIALS_ID = 'ec2-private-key'       // ID trong Jenkins Credential cho SSH key EC2
        EC2_HOST = 'ec2-52-76-220-143.ap-southeast-1.compute.amazonaws.com'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Checking out source code from Git...'
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                echo '🔧 Building project with Maven...'
                sh 'mvn clean package -DskipTests'   // Bỏ qua test nếu bạn muốn nhanh
            }
        }

        stage('Login to Docker Hub') {
            steps {
                echo '🚀 Logging in to Docker Hub...'
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS_ID}",
                                                  passwordVariable: 'DOCKER_PASS',
                                                  usernameVariable: 'DOCKER_USER')]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                }
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                echo '🐳 Building Docker image and pushing to Docker Hub...'
                sh '''
                      docker build -t $DOCKER_IMAGE .
                      docker push $DOCKER_IMAGE
                '''
            }
        }

        stage('Deploy to EC2') {
            steps {
                echo '🚀 Deploying new Docker image to EC2...'
                sshagent(credentials: ["${PRIVATE_KEY_CREDENTIALS_ID}"]) {
                    sh """
                        ssh -o BatchMode=yes -o StrictHostKeyChecking=no ${EC2_HOST} << 'EOF'
                            set -e
                            echo "🧹 Cleaning up old containers..."
                            sudo docker-compose down
                            sudo docker pull ${DOCKER_IMAGE}
                            echo "🚀 Starting new version..."
                            sudo docker-compose up -d
                        EOF
                    """
                }
            }
        }
    }
}
