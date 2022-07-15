import { useNavigate } from 'react-router-dom';

import styles from './styles';

type SpaceCardProps = {
  spaceName: string;
  imageUrl: string;
  id: number;
};

const SpaceCard: React.FC<SpaceCardProps> = ({ spaceName, imageUrl, id }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(id.toString());
  };

  return (
    <div css={styles.spaceCard({ imageUrl })} onClick={handleClick}>
      <span css={styles.title}>{spaceName}</span>
    </div>
  );
};

export default SpaceCard;
