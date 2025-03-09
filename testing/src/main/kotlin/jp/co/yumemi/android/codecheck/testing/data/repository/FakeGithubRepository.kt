package jp.co.yumemi.android.codecheck.presentation.data.repository

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepositoryItemInfo
import jp.co.yumemi.android.codecheck.domain.repository.GithubRepository

class FakeGithubRepository : GithubRepository {
    /**
     * 入力した値を使って、Github Repositoriesを取得し、アプリで使う情報に加工して返す。
     * @param inputText 入力した文字列
     * @return [List] [SearchedRepositoryItemInfo]のリスト
     */
    override fun getSearchedRepositoryItemInfo(inputText: String): List<SearchedRepositoryItemInfo> {
        return listOf()
    }
}
