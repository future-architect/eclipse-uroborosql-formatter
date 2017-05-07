select /* _SQL_ID_ */
	EMP.EMP_NO		AS	EMP_NO
,	EMP.FIRST_NAME	AS	FIRST_NAME
,	EMP.LAST_NAME	AS	LAST_NAME
,	EMP.BIRTH_DATE	AS	BIRTH_DATE
,	EMP.GENDER		AS	GENDER
FROM
	EMPLOYEE	EMP
/*BEGIN*/
WHERE
/*IF SF.isNotEmpty(emp_no)*/
AND	EMP.EMP_NO		=	/*emp_no*/	1
/*END*/
/*IF SF.isNotEmpty(first_name)*/
AND	EMP.FIRST_NAME	=	/*first_name*/	'Bob'
/*END*/
/*IF SF.isNotEmpty(last_name)*/
AND	EMP.LAST_NAME	=	/*last_name*/	'Smith'
/*END*/
/*IF SF.isNotEmpty(birth_date_from)*/
AND	EMP.BIRTH_DATE	>=	/*birth_date_from*/	'1990-10-10'
/*END*/
/*IF SF.isNotEmpty(birth_date_to)*/
AND	EMP.BIRTH_DATE	<	/*birth_date_to*/	'1990-10-10'
/*END*/
/*IF gender_list != null*/
AND	EMP.GENDER		IN	/*gender_list*/	('M')
/*END*/
/*IF female != null and female*/
AND	EMP.GENDER		=	/*#CLS_GENDER_FEMALE*/	'M'
/*END*/
/*END*/
;
