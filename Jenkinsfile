pipeline {
    agent any

    stages {
        stage('clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('package') {
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
            }
        }
        stage('deploy'){
            steps{
                sh 'cp /home/ec2-user/.jenkins/workspace/pipeline1/target/cars.war /home/ec2-user/apache-tomcat-8.5.60/webapps'
            }
        }
    }
}
