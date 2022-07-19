import { useEffect, useState } from 'react';
import { useMutation, useQuery } from 'react-query';

import Button from '@/components/common/Button';
import Input from '@/components/common/Input';

import slackApi from '@/apis/slack';

import styles from './styles';

const SlackUrlBox: React.FC<{ jobName: string; jobId: number }> = ({ jobName, jobId }) => {
  const { data } = useQuery(['slackUrl', 1], () => slackApi.getSlackUrl(1), {
    retry: 3,
  });
  const { mutate } = useMutation(() => slackApi.putSlackUrl(1), {
    retry: 3,
  });

  const [url, setUrl] = useState('');

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUrl(e.target.value);
  };

  const onClick = () => {
    mutate();
  };

  useEffect(() => {
    if (data) {
      setUrl(data.slackUrl);
    }
  }, []);

  return (
    <div css={styles.slackUrlBox}>
      <span css={styles.jobName}>{jobName}</span>
      <Input css={styles.input} value={url} placeholder="Slack URL" onChange={onChange} />
      <Button css={styles.button} onClick={onClick}>
        편집
      </Button>
    </div>
  );
};

export default SlackUrlBox;
