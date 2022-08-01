declare namespace Cypress {
  interface Chainable {
    setToken(): void;
    spaceEnter(): void;
    getSpaces(): void;
    getJobs(): void;
    getSpaceInfo(): void;
    getRunningTaskActive_true(jobId: number): void;
    getRunningTaskActive_false(jobId: number): void;
    postNewRunningTasks(jobId: number): void;
    getRunningTasks(): void;
    postCheckTask(taskId: number): void;
    postJobComplete(): void;
  }
}
