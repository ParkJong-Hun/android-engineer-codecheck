package jp.co.yumemi.android.codecheck.domain.repository

import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository

interface GithubRepository {
    /**
     * 入力した値を使って、Github Repositoriesを取得し、アプリで使う情報に加工して返す。
     * @param inputText 入力した文字列
     * @return [List] [SearchedRepository]のリスト
     */
    suspend fun getSearchedRepositoryItemInfo(query: String): List<SearchedRepository>
}
