/**  @jsxImportSource @emotion/react */
import { useNavigate } from 'react-router-dom';
import CardTitle from '../_common/CardTitle';
import styles from './styles';

type SpaceCardProps = {
  spaceName: string;
  imageURL: string;
  id: number;
};

const SpaceCard = ({ spaceName, imageURL, id }: SpaceCardProps) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(id.toString());
  };

  return (
    <div css={styles.spaceCard({ imageURL })} onClick={handleClick}>
      <CardTitle>{spaceName}</CardTitle>
    </div>
  );
};

export default SpaceCard;
