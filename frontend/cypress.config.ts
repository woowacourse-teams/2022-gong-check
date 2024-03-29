import { defineConfig } from 'cypress';

export default defineConfig({
  projectId: 'abtuyq',
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
  env: {
    'cypress-react-selector': {
      root: '#root',
      modal: '#modal',
      toast: '#toast',
    },
  },
  chromeWebSecurity: false,
});
