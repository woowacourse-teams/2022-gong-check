import { useRoutes } from 'react-router-dom';
import routes from './Routes';

const App = () => {
  const content = useRoutes(routes);

  return (
    <div>
      <h1>Header</h1>
      {content}
    </div>
  );
};

export default App;
