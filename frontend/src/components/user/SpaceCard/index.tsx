import { useNavigate } from 'react-router-dom';

import { DEFAULT_IMAGE } from '@/constants/user';

import frontArrow from '@/assets/frontArrow.svg';

import styles from './styles';

type SpaceCardProps = {
  spaceName: string;
  imageUrl: string;
  id: number;
};

const SpaceCard: React.FC<SpaceCardProps> = ({ spaceName, imageUrl, id }) => {
  const navigate = useNavigate();

  const onClick = () => {
    navigate(id.toString());
  };

  return (
    <div css={styles.spaceCard(imageUrl)} onClick={onClick}>
      <span css={styles.title}>{spaceName}</span>
      <img css={styles.arrow} src={frontArrow} alt="" />
    </div>
  );
};

export default SpaceCard;
