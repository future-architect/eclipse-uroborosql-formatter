# coding:utf-8
'''
Created on 2016/07/05

@author: ota
'''
import unittest
import os
import codecs
import sys
from uroborosqlfmt import api
from uroborosqlfmt.config import LocalConfig
from uroborosqlfmt.commentsyntax import UroboroSqlCommentSyntax


class Test(unittest.TestCase):

    def __init__(self, methodName='runTest'):
        unittest.TestCase.__init__(self, methodName=methodName)

    def test(self):
        pass

    def _read_file(self, path):
        target_file = codecs.open(path, "r", "utf-8")
        ret = target_file.read()
        target_file.close()
        return ret

    def _split(self, text):
        return self.__encode(text).replace('\r\n','\n').replace('\r','\n').split("\n")

    def __encode(self, text):
        if sys.version_info[0] < 3 and isinstance(text, unicode):
            return text.encode("utf-8")
        else:
            return text

def __setup_tests():
    curr = os.getcwd()
    i = curr.index("sql-formatter-for-python")
    root = curr[0:i]
    testfiles = os.path.join(root, "testfiles", "uroboros")
    test_target = os.path.join(testfiles, "test")
    test_out = os.path.join(root, "sql-formatter-for-python", "target", "testout", "uroboros")
    answer_target = os.path.join(testfiles, "answer")

    for root, dirs, files in os.walk(test_out, topdown=False):
        for name in files:
            os.remove(os.path.join(root, name))
        for name in dirs:
            os.rmdir(os.path.join(root, name))

    local_config = LocalConfig()
    local_config.set_case('upper')
    local_config.set_commentsyntax(UroboroSqlCommentSyntax())
    api.format_dir(test_target, test_out, local_config)

    for file_name, full_path in api.find_all_sql_files(test_out):


        def wrapper(file_name, full_path):
            def ret_fn(self):
                print("test: " + file_name)
                text_sql = self._read_file(full_path).strip()
                text_answer = self._read_file(os.path.join(answer_target, file_name)).strip()

                sql = self._split(text_sql)
                answer = self._split(text_answer)
                try:
                    for i, _ in enumerate(sql):
                        if len(answer) > i:
                            self.assertEqual(sql[i], answer[i], file_name + "@" + str(i + 1) + u"行目")
                        else:
                            self.fail(file_name + "@" + str(i + 1) + " out of rows")
                except:
                    raise
            return ret_fn

        setattr(Test, 'test_file_{}'.format(file_name), wrapper(file_name, full_path))

__setup_tests()

if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
