import apiAuth from './githubAuth';
import apiImage from './image';
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
  ...apiAuth,
  ...apiImage,
};

export default apis;
