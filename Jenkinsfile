pipeline {
    agent any

    environment {
        // AWS setup
        AWS_DEFAULT_REGION = 'us-east-1'
        S3_BUCKET = '<BUCKET NAME>' 

        // App Server setup
        APP_SERVER_IP = '172.31.25.210' // Replace with actual IP
        APP_SERVER_USER = 'ec2-user'
        SSH_CREDENTIAL_ID = 'app-server-ssh-key' // ID of the credential in Jenkins

        USER = 'ec2-user'
        API_GATEWAY_IP = '172.31.19.231'
        EUREKA_SERVER_IP = '172.31.31.10'
        AUTH_SERVER_IP = '172.31.20.216'
        USER_SERVER_IP = '172.31.18.195'

        UTIL_TAG = 'util'

        DIR_API_GATEWAY =   'api-gateway-demo'
        DIR_AUTH_SERVER =   'auth-server'
        DIR_EUREKA_SERVER = 'eureka-demo'
        DIR_USER_SERVER =   'user-server'
        DIR_UTIL =          'util'
    }

    tools {
        // defined in Jenkins global tool configuration
        maven 'Maven 3.9.12' 
        nodejs 'NodeJS 24'
    }

    stages {
        stage('Build Backend') {
            steps {
                // the zip helper script
                sh "chmod +x ./zip_files.sh"

                dir(DIR_UTIL)
                {
                    sh 'mvn clean install -DskipTests'
                }

                dir(DIR_API_GATEWAY)
                {
                    sh 'mvn clean package -DskipTests'
                }
                dir(DIR_AUTH_SERVER)
                {
                    sh 'mvn clean package -DskipTests'
                }
                dir(DIR_EUREKA_SERVER)
                {
                    sh 'mvn clean package -DskipTests'
                }
                dir(DIR_USER_SERVER)
                {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        /*
        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    // Inject Backend IP into React build
                    sh "VITE_API_URL=http://${APP_SERVER_IP}:80/api/items npm run build"
                }
            }
        }*/

        stage('Deploy Backend Eureka') {
            steps {
                sshagent([SSH_CREDENTIAL_ID]) {

                    // Compress
                    sh "sudo ./zip_files.sh project.zip ./${DIR_EUREKA_SERVER}/zip.lst"

                    // Transfer
                    sh "scp -o StrictHostKeyChecking=no project.zip ${USER}@${EUREKA_SERVER_IP}:/home/${USER}/project.zip"

                    // Run Docker commands on remote server
                    sh """
                        ssh -o StrictHostKeyChecking=no ${USER}@${EUREKA_SERVER_IP} '
                            # Unzip
                            unzip project.zip

                            # Build / Run Docker
                            cd ./${DIR_EUREKA_SERVER}
                            docker build -t eureka .
                            docker stop eureka || true
                            docker rm eureka || true
                            docker run -d --name eureka \\
                                -p 80:8080 \\
                                eureka
                        '
                    """
                }
            }
        }
        stage('Deploy Backend Auth') {
            steps {
                sshagent([SSH_CREDENTIAL_ID]) {

                    // Compress
                    sh "rm ./project.zip"
                    sh "sudo ./zip_files.sh project.zip ./${DIR_AUTH_SERVER}/zip.lst"

                    // Transfer
                    sh "scp -o StrictHostKeyChecking=no project.zip ${USER}@${AUTH_SERVER_IP}:/home/${USER}/project.zip"

                    // Run Docker commands on remote server
                    sh """
                        ssh -o StrictHostKeyChecking=no ${USER}@${AUTH_SERVER_IP} '
                            # Unzip
                            unzip project.zip

                            # Build / Run Docker
                            cd ./${DIR_AUTH_SERVER}
                            docker compose up --build
                        '
                    """
                }
            }
        }
        /*
        stage('Deploy Frontend (S3)') {
            steps {
                dir('frontend') {
                    withAWS(credentials: 'aws-credentials', region: AWS_DEFAULT_REGION) {
                        sh "aws s3 sync dist/ s3://${S3_BUCKET} --delete"
                    }
                }
            }
        }*/
    }
}
