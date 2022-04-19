package com.github.af2905.movieland.presentation.feature.detail.persondetail.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.af2905.movieland.R
import com.github.af2905.movieland.helper.extension.getFullPathToImage
import com.github.af2905.movieland.presentation.model.item.PersonItem
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PersonItem(item: PersonItem) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        GlideImage(
            imageModel = item.profilePath,
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.ic_account),
            modifier = Modifier
                .heightIn(300.dp, 500.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = item.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        item.birthday?.let { birthday ->
            Spacer(Modifier.height(16.dp))
            Text(text = birthday, style = MaterialTheme.typography.body2)
        }

        item.placeOfBirth?.let {
            Spacer(Modifier.height(16.dp))
            Text(text = it, style = MaterialTheme.typography.body2)
        }

        Spacer(Modifier.height(16.dp))
        Text(text = item.knownForDepartment, style = MaterialTheme.typography.body2)
    }
}

@Composable
fun PersonBiography(item: PersonItem) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.person_detail_biography),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
        )
        Spacer(Modifier.height(16.dp))
        Text(text = item.biography)
    }
}

@Preview
@Composable
fun PreviewPersonItem() {

    val person = PersonItem(
        id = 287,
        name = "Brad Pitt",
        birthday = "1963-12-18",
        knownForDepartment = "Acting",
        deathday = null,
        alsoKnownAs = listOf(
            "برد پیت",
            "Бред Питт",
            "Бред Пітт",
            "Buratto Pitto",
            "Брэд Питт",
            "畢·彼特",
            "ブラッド・ピット",
            "브래드 피트",
            "براد بيت",
            "แบรด พิตต์"
        ),
        gender = 2,
        popularity = 10.647,
        biography = "William Bradley \"Brad\" Pitt (born December 18, 1963) " +
                "is an American actor and film producer. " +
                "Pitt has received two Academy Award nominations and four Golden Globe Award " +
                "nominations, winning one. He has been described as one of the world's most " +
                "attractive men, a label for which he has received substantial media attention. " +
                "Pitt began his acting career with television guest appearances, " +
                "including a role on the CBS prime-time soap opera Dallas in 1987. " +
                "He later gained recognition as the cowboy hitchhiker who seduces Geena Davis's " +
                "character in the 1991 road movie Thelma & Louise. Pitt's first leading roles " +
                "in big-budget productions came with A River Runs Through It (1992) " +
                "and Interview with the Vampire (1994). He was cast opposite Anthony Hopkins " +
                "in the 1994 drama Legends of the Fall, which earned him his first " +
                "Golden Globe nomination. In 1995 he gave critically acclaimed performances " +
                "in the crime thriller Seven and the science fiction film 12 Monkeys, " +
                "the latter securing him a Golden Globe Award for Best Supporting Actor " +
                "and an Academy Award nomination.\n\nFour years later, in 1999, Pitt starred " +
                "in the cult hit Fight Club. He then starred in the major international " +
                "hit as Rusty Ryan in Ocean's Eleven (2001) and its sequels, Ocean's Twelve" +
                " (2004) and Ocean's Thirteen (2007). His greatest commercial successes" +
                " have been Troy (2004) and Mr. & Mrs. Smith (2005).\n\nPitt received his" +
                " second Academy Award nomination for his title role performance in the 2008" +
                " film The Curious Case of Benjamin Button. Following a high-profile " +
                "relationship with actress Gwyneth Paltrow, Pitt was married to actress " +
                "Jennifer Aniston for five years. Pitt lives with actress Angelina Jolie " +
                "in a relationship that has generated wide publicity. He and Jolie have six " +
                "children—Maddox, Pax, Zahara, Shiloh, Knox, and Vivienne.\n\nSince beginning " +
                "his relationship with Jolie, he has become increasingly involved in social " +
                "issues both in the United States and internationally. " +
                "Pitt owns a production company named Plan B Entertainment, whose productions " +
                "include the 2007 Academy Award winning Best Picture, The Departed.",
        placeOfBirth = "Shawnee, Oklahoma, USA",
        adult = false,
        homepage = null,
    ).apply {
        profilePath = "/kU3B75TyRiCgE270EyZnHjfivoq.jpg".getFullPathToImage()
    }

    PersonItem(item = person)
}