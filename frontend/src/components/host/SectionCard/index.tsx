import ToggleSwitch from '@/components/common/ToggleSwitch';
import useToggleSwitch from '@/components/common/ToggleSwitch/useToggleSwitch';
import SectionCardDefault from '@/components/host/SectionCardDefault';
import SectionCardDetail from '@/components/host/SectionCardDetail';

import { SectionType } from '@/types';

import styles from './styles';

interface SectionCardProps {
  section: SectionType;
  sectionIndex: number;
}

const SectionCard: React.FC<SectionCardProps> = ({ section, sectionIndex }) => {
  const { toggle, onClickToggle } = useToggleSwitch();

  const toggleLeft = !toggle;

  return (
    <div css={styles.container}>
      <div css={styles.switchWrapper}>
        <ToggleSwitch toggle={toggle} onClickSwitch={onClickToggle} left="기본" right="상세정보" />
      </div>
      {toggleLeft ? (
        <SectionCardDefault section={section} sectionIndex={sectionIndex} />
      ) : (
        <SectionCardDetail section={section} sectionIndex={sectionIndex} />
      )}
    </div>
  );
};

export default SectionCard;
