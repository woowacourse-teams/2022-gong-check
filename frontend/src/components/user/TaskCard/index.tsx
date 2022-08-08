import { RiInformationLine } from 'react-icons/ri';

import CheckBox from '@/components/common/Checkbox';
import DetailInfoModal from '@/components/user/DetailInfoModal';

import useModal from '@/hooks/useModal';

import apis from '@/apis';

import { ID, TaskType } from '@/types';

import styles from './styles';

type TaskCardProps = {
  tasks: TaskType[];
  getSections: () => void;
};

const TaskCard: React.FC<TaskCardProps> = ({ tasks, getSections }) => {
  const { openModal } = useModal();

  const onClickCheckBox = async (
    e: React.MouseEvent<HTMLElement, MouseEvent> | React.ChangeEvent<HTMLElement>,
    id: ID
  ) => {
    e.preventDefault();
    await apis.postCheckTask(id);
    getSections();
  };

  const onClickTaskDetail = (task: TaskType) => {
    openModal(<DetailInfoModal name={task.name} imageUrl={task.imageUrl} description={task.description} />);
  };

  return (
    <div css={styles.taskCard}>
      {tasks.map((task, id) => (
        <div key={id} css={styles.task}>
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

export default TaskCard;
