import styles from './styles';

const Profile: React.FC = () => {
  const PROFILE_DATA = { name: 'cks3066', imageUrl: 'https://avatars.githubusercontent.com/u/62434898?v=4' };

  return (
    <div css={styles.profile}>
      <img src={PROFILE_DATA.imageUrl} alt="cks3066" width={36} height={36} />
      <p>{PROFILE_DATA.name}</p>
    </div>
  );
};

export default Profile;
