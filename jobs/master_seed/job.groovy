#!/usr/bin/env groovy

def entities = [
  [
    name: 'sample'
  ],
  [
    name: 'test1'
  ]
].each { item ->

  entity = item['name']

  def jobname = 'seed_job'

  folder("${entity}")
  job("${entity}/${jobname}") {
    label('master-node')
    description "Seed Job for ${entity}"
    disabled(false)
    concurrentBuild(false)
    logRotator(-1, 5)

    scm {
      git {
        remote {
          url("${GIT_BASE_URL}/${GIT_PIPELINE_REPO}")
          //credentials('github_credential')
        }
        branch('master')
      }
    }

    steps {
      jobDsl {
        targets("${entity}/seeds/*.groovy")
        sandbox(false)
        ignoreExisting(false)
      }
    }
  }
}
