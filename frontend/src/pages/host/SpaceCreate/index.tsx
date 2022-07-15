import SpaceInfo from '@/components/host/SpaceInfo';

import styles from './styles';

const SpaceCreate: React.FC = () => {
  const spaceName = '';
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log('click');
  };

  return (
    <div css={styles.layout}>
      <SpaceInfo onSubmit={handleSubmit} inputText={spaceName} />
    </div>
  );
};

export default SpaceCreate;
