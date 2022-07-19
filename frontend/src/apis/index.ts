import apisJob from './job';
import apisPassword from './password';
import apisSlack from './slack';
import apisSpace from './space';
import apisSubmission from './submission';
import apisTask from './task';

const apis = {
  ...apisJob,
  ...apisSpace,
  ...apisSubmission,
  ...apisPassword,
  ...apisSlack,
  ...apisTask,
};

export default apis;
