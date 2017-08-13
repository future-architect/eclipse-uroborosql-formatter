# coding:utf-8
'''
Created on 2017/01/27

@author: ota
'''

import unittest
import inspect
from uroborosqlfmt import api

class Test(unittest.TestCase):

    def test_def_args(self):
        args = api._parse_args('input output'.split())

        self.assertEqual(args.input_path, 'input')
        self.assertEqual(args.output_path, 'output')
        self.assertEqual(args.mode, 'file')
        self.assertEqual(args.case, None)
        self.assertEqual(args.escapesequence_u005c, False)
        self.assertEqual(args.comment_syntax, 'uroborosql')
        self.assertEqual(args.reserved_words_file_path, None)

    def test_args01(self):
        args = api._parse_args('input output -m directory -a upper -B -c doma'.split())

        self.assertEqual(args.input_path, 'input')
        self.assertEqual(args.output_path, 'output')
        self.assertEqual(args.mode, 'directory')
        self.assertEqual(args.case, 'upper')
        self.assertEqual(args.escapesequence_u005c, True)
        self.assertEqual(args.comment_syntax, 'doma')

    def test_args02(self):
        args = api._parse_args('input output -m directory -a lower -B -c doma'.split())

        self.assertEqual(args.input_path, 'input')
        self.assertEqual(args.output_path, 'output')
        self.assertEqual(args.mode, 'directory')
        self.assertEqual(args.case, 'lower')
        self.assertEqual(args.escapesequence_u005c, True)
        self.assertEqual(args.comment_syntax, 'doma')

    def test_args03(self):
        args = api._parse_args('input output -m directory -a capitalize -B -c doma'.split())

        self.assertEqual(args.input_path, 'input')
        self.assertEqual(args.output_path, 'output')
        self.assertEqual(args.mode, 'directory')
        self.assertEqual(args.case, 'capitalize')
        self.assertEqual(args.escapesequence_u005c, True)
        self.assertEqual(args.comment_syntax, 'doma')

    def test_args04(self):
        args = api._parse_args('input output -m directory -a upper -B -c doma -r reserved_word_file'.split())

        self.assertEqual(args.input_path, 'input')
        self.assertEqual(args.output_path, 'output')
        self.assertEqual(args.mode, 'directory')
        self.assertEqual(args.case, 'upper')
        self.assertEqual(args.escapesequence_u005c, True)
        self.assertEqual(args.comment_syntax, 'doma')
        self.assertEqual(args.reserved_words_file_path, 'reserved_word_file')

    def test_args05(self):
        args = api._parse_args('input output -m directory -a lower -e upper -B -c doma -r reserved_word_file'.split())

        self.assertEqual(args.input_path, 'input')
        self.assertEqual(args.output_path, 'output')
        self.assertEqual(args.mode, 'directory')
        self.assertEqual(args.case, 'lower')
        self.assertEqual(args.reserved_case, 'upper')
        self.assertEqual(args.escapesequence_u005c, True)
        self.assertEqual(args.comment_syntax, 'doma')
        self.assertEqual(args.reserved_words_file_path, 'reserved_word_file')

    def test_help(self):
        def test1():
            api._parse_args('-h'.split())
        print('****help')
        self.assertRaises(SystemExit, test1)

    def test_version(self):
        def test1():
            api._parse_args('-v'.split())
        print('****version')
        self.assertRaises(SystemExit, test1)

    def test_no_outout(self):
        print('--' + inspect.stack()[0].function)
        def test1():
            api._parse_args('input -m directory'.split())
        self.assertRaises(SystemExit, test1)

        def test2():
            api._parse_args('-m directory input'.split())
        self.assertRaises(SystemExit, test2)


    def test_no_input(self):
        print('--' + inspect.stack()[0].function)
        def test():
            api._parse_args('-m directory'.split())
        self.assertRaises(SystemExit, test)


    def test_illegal_mode(self):
        print('--' + inspect.stack()[0].function)
        def test():
            api._parse_args('input output -m illegal -N -B -c doma'.split())
        self.assertRaises(SystemExit, test)

    def test_illegal_comment_syntax(self):
        print('--' + inspect.stack()[0].function)
        def test():
            api._parse_args('input output -m file -N -B -c doma3'.split())
        self.assertRaises(SystemExit, test)

if __name__ == "__main__":
    unittest.main()
