import { RiInformationLine } from 'react-icons/ri';

import CheckBox from '@/components/common/Checkbox';
import DetailedInfoCardModal from '@/components/user/DetailedInfoCardModal';

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

  const onClickTaskDetail = (task: any) => {
    // TODO: api 명세서 나오면 알맞는 데이터를 넣을 생각입니다.
    const imageUrl = 'https://velog.velcdn.com/images/cks3066/post/258f92c1-32be-4acb-be30-1eb64635c013/image.jpg';
    const description = '어쩌고 저쩌고저쩌고............ㅇㅇㅇㅇ';

    openModal(<DetailedInfoCardModal name={task.name} imageUrl={imageUrl} description={description} />);
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
            <RiInformationLine css={styles.icon} size={20} onClick={() => onClickTaskDetail(task)} />
          </div>
        </div>
      ))}
    </div>
  );
};

export default TaskCard;
