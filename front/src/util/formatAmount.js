export const formatAmount = (str) => {
  const num = str.split(',');
  let 조, 억, 천;

  if (num.length > 2) 조 = Number(num[0].padStart(3, '0').substr(0, 1));
  else num.unshift('');
  억 = Number(
    num[0].padStart(3, '0').substr(2, 1) + num[1].padStart(3, '0').substr(0, 2)
  );
  천 = Number(num[1].padStart(3, '0').substr(2, 1) + num[2]);

  let res = '';
  if (조) res += `${조}조 `;
  if (억) res += `${억}억 `;
  if (천) res += `${천}만`;
  return res.trimEnd() + '원';
};

export const formatAmountDot = (str) => {
  const num = str.split(',');
  let 조, 억, 천;

  if (num.length > 2) 조 = Number(num[0].padStart(3, '0').substr(0, 1));
  else num.unshift('');
  억 = Number(
    num[0].padStart(3, '0').substr(2, 1) + num[1].padStart(3, '0').substr(0, 2)
  );
  천 = Number(num[1].padStart(3, '0').substr(2, 1));

  let res = '';
  if (조) res += `${조}조 `;
  if (억) res += `${억}`;
  if (천) res += `.${천}`;
  return res.trimEnd() + '억';
};
