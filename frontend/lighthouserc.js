module.exports = {
  ci: {
    collect: {
      staticDistDir: './dist',
      startServerCommand: 'npm run start',
      url: ['http://localhost:3000/index.html'],
      collect: {
        numberOfRuns: 3,
      },
    },
    upload: {
      target: 'filesystem',
      outputDir: './lhci_reports',
      reportFilenamePattern: '%%PATHNAME%%-%%DATETIME%%-report.%%EXTENSION%%',
    },
    assert: {
      preset: 'lighthouse:recommended',
    },
  },
};
