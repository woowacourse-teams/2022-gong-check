import { useQuery } from 'react-query';

import apis from '@/apis';

import styles from './styles';

const Profile: React.FC = () => {
  const { data: profileData } = useQuery(['hostProfile'], apis.getHostProfile, {
    suspense: false,
    useErrorBoundary: false,
  });

  return (
    <div css={styles.profile}>
      <img src={profileData?.imageUrl} alt="" width={36} height={36} />
      <p>{profileData?.nickname}</p>
    </div>
  );
};

export default Profile;
