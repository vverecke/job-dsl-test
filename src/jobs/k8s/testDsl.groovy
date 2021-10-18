
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Folder

final String REPOSITORY_NAME = 'vverecke/job-dsl-test'
final String repoUrl = "https://github.com/${REPOSITORY_NAME}"
final String gitUrl = "git@github.com:${REPOSITORY_NAME}.git"

Folder testFolder = folder('dsl-test') {
    displayName 'DSL Test'
}

pipelineJob("${testFolder.name}/hello-world") {
    displayName('Hello World')
    definition {
        cps {
            sandbox false
            script("""
                pipeline {
                    agent {
                        kubernetes {
                            yaml '''
                                apiVersion: v1
                                kind: Pod
                                spec:
                                containers:
                                - name: ubuntu
                                  image: ubuntu
                                  command:
                                  - sleep
                                  args:
                                  - infinity
                            '''
                        }
                    }
                    stages {
                        stage('Hello World') {
                            steps {
                                container(ubuntu) {
                                    sh('echo Hello World!!')
                                }
                            }
                        }
                    }
                }
            """.stripIndent().trim())
        }
    }
}
