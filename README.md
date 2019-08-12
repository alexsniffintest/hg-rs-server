# hg-rs-server

Hunger Games private server

## Deployment

### How To

1. Update the `image.tag` in the [helm/hg-rs-server/values.yaml] to the new version.
2. Deploy a new version of the server by creating a new git tag.
3. Go to TravisCI, a build should be triggered for the new tag. Verify the docker image is pushed successfully in the final step.
4. Login to the server and start an update countdown (10 minutes is recommended so people can finish their games.)
5. Go to our [ArgoCD](35.237.154.238) (Ask for credentials.)
6. Find the application `prod` and then click sync.
7. Check that a new ReplicaSet is created and that the old ReplicaSet scales down to 0 pods.
8. Verify you can login to the server.