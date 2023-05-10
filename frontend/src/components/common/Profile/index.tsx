import { useQuery } from 'react-query';

import apis from '@/apis';

import { ID } from '@/types';

import styles from './styles';

interface ProfileProps {
  hostId: ID;
}

const Profile: React.FC<ProfileProps> = ({ hostId }) => {
  const { data: profileData } = useQuery(['hostProfile'], () => apis.getHostProfile(hostId), {
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
