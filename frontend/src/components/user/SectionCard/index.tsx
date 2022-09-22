import SectionInfoPreview from './SectionInfoPreview';
import { RiInformationLine } from '@react-icons/all-files/ri/RiInformationLine';

import Button from '@/components/common/Button';
import CheckBox from '@/components/common/Checkbox';
import DetailInfoModal from '@/components/user/DetailInfoModal';

import useModal from '@/hooks/useModal';

import apis from '@/apis';

import { ID, SectionType, TaskType } from '@/types';

import styles from './styles';

type SectionCardProps = {
  section: SectionType;
  sectionsAllCheckMap: any;
  onClickSectionDetail: any;
  onClickSectionAllCheck: any;
};

const SectionCard: React.FC<SectionCardProps> = ({
  section,
  sectionsAllCheckMap,
  onClickSectionDetail,
  onClickSectionAllCheck,
}) => {
  const { openModal } = useModal();

  const onClickCheckBox = (e: React.MouseEvent<HTMLElement, MouseEvent> | React.ChangeEvent<HTMLElement>, id: ID) => {
    e.preventDefault();
    apis.postCheckTask(id);
  };

  const onClickTaskDetail = (task: TaskType) => {
    openModal(<DetailInfoModal name={task.name} imageUrl={task.imageUrl} description={task.description} />);
  };

  return (
    <div css={styles.sectionCard}>
      <div css={styles.locationHeader}>
        <p css={styles.locationName}>{section.name}</p>
        <div css={styles.locationHeaderRightItems}>
          {!sectionsAllCheckMap.get(`${section.id}`) && (
            <Button
              css={styles.sectionAllCheckButton}
              type="button"
              onClick={() => onClickSectionAllCheck(section.id!)}
            >
              ALL
            </Button>
          )}
          {(section.imageUrl || section.description) && (
            <SectionInfoPreview imageUrl={section.imageUrl} onClick={() => onClickSectionDetail(section)} />
          )}
        </div>
      </div>

      {section.tasks.map((task, index) => (
        <div key={task.id} css={styles.task}>
          <CheckBox
            onChange={e => onClickCheckBox(e, task.id)}
            checked={task.checked || false}
            id={JSON.stringify(task.id)}
          />
          <div css={styles.textWrapper}>
            <span>{task.name}</span>
            {(task.imageUrl || task.description) && (
              <RiInformationLine css={styles.icon} size={20} onClick={() => onClickTaskDetail(task)} />
            )}
          </div>
        </div>
      ))}
    </div>
  );
};

export default SectionCard;
